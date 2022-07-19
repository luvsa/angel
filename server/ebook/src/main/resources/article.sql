DROP TABLE IF EXISTS Article;
create table Article
(
    ID          char(32)             not null comment '文章标识' primary key,
    Title       varchar(64)          not null comment '文章标题',
    Priority    int                  null comment '文章序号',
    Book_ID     char(32)             not null comment '图书标识',
    Path        varchar(100)         null comment '文章路径',
    Enable      tinyint(1) default 0 null comment '文章是否可用',
    Create_Time datetime             not null comment '数据创建时间',
    Update_Time datetime             null comment '数据更新时间',
    Creator     varchar(32)          null comment '创建者信息',
    Updater     varchar(32)          null comment '更新者信息',
    Remark      text                 null comment '备注',
    Deleted     tinyint(1) default 0 null comment '逻辑删除字段'
) collate = utf8mb4_general_ci;