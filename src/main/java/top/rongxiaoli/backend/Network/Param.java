package top.rongxiaoli.backend.Network;

import java.nio.charset.StandardCharsets;

public class Param {
    public StringBuilder Par;

    public Param() {
        this.Par = new StringBuilder();
    }

    /**
     * Append a param to params.
     *
     * @param name  Param name.
     * @param value Param value.
     */
    public void Append(String name, String value) {
        if (Par.length() == 0) {
            Par.append('?').append(name).append('=').append(value);
        } else {
            Par.append('&').append(name).append('=').append(value);
        }
    }

    public void Append(String name, String value, boolean unicodeEncodeMode) {
        String finalValue = value;
        if (unicodeEncodeMode) {
            byte[] ParByte = value.getBytes(StandardCharsets.UTF_8);
            StringBuilder encodedParByte = new StringBuilder();
            for (byte singleByte :
                    ParByte) {
                encodedParByte.append("%").append((Integer.toHexString((singleByte & 0x000000ff) | 0xffffff00)).substring(6));
            }
            finalValue = encodedParByte.toString();
        }
        Append(name, finalValue);
    }

    /**
     * Get params.
     *
     * @return The param added to the end of Url.
     */
    public String BuildGet() {
        if (Par == null) {
            return "";
        }
        return Par.toString();
    }
    /**
     * Get params.
     *
     * @return The param added to the end of Url.
     */
    public String BuildPost() {
        if (Par == null) {
            return "";
        }
        return Par.toString().substring(1);
    }
}
