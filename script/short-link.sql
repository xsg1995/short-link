create table t_short_link (
short_link varchar(20) primary key comment '短链接',
salt int not null comment '生成短链接用到的salt，解决散列冲突',
prefix varchar(3) default null comment '自定义的短链接前缀',
url varchar(500) not null comment '原始url'
)engine=innoDB default charset=utf8mb4 comment '短链接信息表';