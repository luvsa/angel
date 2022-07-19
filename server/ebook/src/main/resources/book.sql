drop table if exists Book;
create table Book
(
    ID          char(32)             not null comment '图书标识' primary key,
    Name        varchar(64)          not null comment '图书名称',
    Author      varchar(64)          null comment '图书作者',
    Avatar      varchar(128)         null comment '封面图片的URL请求地址',
    Type        int                  null comment '图书类型',
    Kind        int                  null comment '内容类型',
    Status      int                  null comment '图书状态',
    Description text                 null comment '图书简介',
    Create_Time datetime             not null comment '数据创建时间',
    Update_Time datetime             null comment '数据更新时间',
    Creator     varchar(32)          null comment '创建者信息',
    Updater     varchar(32)          null comment '更新者信息',
    Remark      text                 null comment '备注',
    Deleted     tinyint(1) default 0 null comment '逻辑删除字段'
) comment '图书信息';