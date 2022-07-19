drop table if exists Sys_Dict_Item;
create table Sys_Dict_Item
(
    ID          char(32)             not null comment '字典项标识' primary key,

    Dict_ID     char(32)             not null comment '字典标识',
    Name       varchar(32)         not null comment '前端显示名称0',
    Label       varchar(32)         not null comment '前端显示名称',
    Value       varchar(32)         not null comment '前端数据的值',

    Create_Time datetime             not null comment '数据创建时间',
    Update_Time datetime             null comment '数据更新时间',
    Creator     varchar(32)          null comment '创建者信息',
    Updater     varchar(32)          null comment '更新者信息',
    Remark      text                 null comment '备注',
    Deleted     tinyint(1) default 0 null comment '逻辑删除字段'
) comment '字典项信息';
