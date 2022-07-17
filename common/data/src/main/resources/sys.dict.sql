drop table if exists Sys_Dict;
create table Sys_Dict
(
    ID          char(32)             not null comment '字典标识' primary key,

    Alias       varchar(32)          null comment '字典别名',
    Name        varchar(32)          null comment '字典名称',

    Create_Time datetime             not null comment '数据创建时间',
    Update_Time datetime             null comment '数据更新时间',
    Creator     varchar(32)          null comment '创建者信息',
    Updater     varchar(32)          null comment '更新者信息',
    Remark      text                 null comment '备注',
    Deleted     tinyint(1) default 0 null comment '逻辑删除字段'
) comment '字典信息';
