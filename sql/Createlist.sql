create user ohgiraffers@'%' identified by 'ohgiraffers';

create database conveniencestoredb;

grant all privileges on conveniencestoredb.* to ohgiraffers@'%';

show grants for ohgiraffers@'%';