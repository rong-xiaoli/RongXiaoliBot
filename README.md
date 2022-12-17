# RongXiaoliBot

## 许可证 Licence

本插件沿用[mirai框架](https://github.com/mamoe/mirai)所使用的AGPL-3.0许可证。

**不许商用**。可以自行**私人使用**，但是**不能未经允许直接使用我的任何一部分源码**。

This plugin uses the AGPL-3.0 license which [mirai framework](https://github.com/mamoe/mirai) uses.

**Commercial use is prohibited**. **Private use** is allowed, but **unconfirmed directedly using any part of my code is not allowed**.

## 功能 Modules

本机器人可提供如下功能：

* 随机图片；（本功能使用Lolicon API开发。API使用说明请参照[链接](https://api.lolicon.app/#/setu)。)
* 戳一戳随机消息回复；
* 广播来自主人的消息；
* 自动同意入群邀请、朋友申请；
* 开关功能；
* 紧急停止全部功能；

正在开发的功能：

* [ ]  每日签到

This bot has these modules:

* Random pictures; (This function is developed based on Lolicon API. API docs please refer to [this link](https://api.lolicon.app/#/setu).)
* Random message while poked;
* Broadcast message which is from bot owner;
* Auto accept group invitation, friend invitation;
* Turn on and off selected modules;
* Stop all modules at once;

Modules WIP:

* [ ]  Daily sign

## 命令 Command

以下表格提供了各命令的命令参数，括号内的是该功能的名称，在管理功能时会使用。

在命令+参数一列,

圆括号()代表该参数为必填项，

方括号[]代表该参数为非必填项。

带有*号的是不可被管理功能关闭的功能。


| 名称                        | 命令+参数                             | 描述                             |
| --------------------------- | ------------------------------------- | -------------------------------- |
| 自动加入(AutoAccept)        | \                                     | 自动加入                         |
| *机器人管理命令(BotCommand) | /help [moduleName]                    | 获取（某一功能的）帮助           |
| *机器人管理命令(BotCommand) | /manage (enable/disable) (moduleName) | 启用/禁用某一功能                |
| *机器人管理命令(BotCommand) | /status                               | 获取各功能状态                   |
| 广播(Broadcast)             | /broadcast (message)                  | 向所有好友和群发布来自主人的信息 |
| 每日签到(DailySign)         | Rsign                                 | 签到                             |
| *紧急停止(EmergencyStop)    | /stop                                 | 停止所有功能                     |
| *紧急停止(EmergencyStop)    | /start                                | 开启原本状为开启的功能           |
| 随机图片(setu)              | setu [keyword1] [keyword2] ...        | 获取一张涩图                     |
| 戳一戳(PokeAction)          | \                                     | 戳一戳事件响应                   |

The chart below offers every parameters of each command, the module name is in the brackets, which will be used in managing modules.

In the "Command + Parameters" row,

round brackets() represents that this parameter is a must-fill parameter,

square brackets[] represents that this parameter is an optional parameter.

Modules marked * cannot be disabled by management module.


| Name                                | Command + Parameters                  | Description                                                  |
| ----------------------------------- | ------------------------------------- | ------------------------------------------------------------ |
| Auto accept(AutoAccept)             | \                                     | Auto accept invitations.                                     |
| *Bot management command(BotCommand) | /help [moduleName]                    | Get the help context of a module.                           |
| *Bot management command(BotCommand) | /manage (enable/disable) (moduleName) | Enable/Disable a module.                                     |
| *Bot management command(BotCommand) | /status                               | Get the modules' status                                     |
| Message broadcast(Broadcast)        | /broadcast (message)                  | Send the message from bot owner to every friends and groups. |
| Daily sign(DailySign)               | Rsign                                 | Sign                                                         |
| *Emergency stop(EmergencyStop)      | /stop                                 | Stop all modules.                                            |
| *Emergency stop(EmergencyStop)      | /start                                | Open all modules which were originally on.                   |
| Random picture(setu)                | setu [keyword1] [keyword2] ...        | Get a random picture.                                        |
| Poke(PokeAction)                    | \                                     | Respond to poke action.                                      |
