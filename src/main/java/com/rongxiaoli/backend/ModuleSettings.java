package com.rongxiaoli.backend;

public interface ModuleSettings {
    /**
     * This part is for judging whether the module should respond to the event.
     */
    default void responseNudge() { return; }
    default void responseFriendMessage() { return; }
    default void responseGroupMessage() { return; }
    default void responseFriendAddInvite() { return; }
    default void responseFriendAdd() { return; }
    default void responseGroupAddInvite() { return; }
    default void responseGroupAdd() { return; }
}
