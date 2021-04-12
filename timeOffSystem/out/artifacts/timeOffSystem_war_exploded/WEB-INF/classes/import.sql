INSERT INTO t_category (id,c_name,c_disabled,c_active) VALUES (1,'role',0,1);
INSERT INTO t_category (id,c_name,c_disabled,c_active) VALUES (2,'leaveStatus',0,1);
INSERT INTO t_category (id,c_name,c_disabled,c_active) VALUES (3,'dateTime',0,1);
INSERT INTO t_categoryElement (id,c_code,c_name,c_category,c_disabled,c_active) VALUES (1,'100','برنامه نویس',1,0,1);
INSERT INTO t_categoryElement (id,c_code,c_name,c_category,c_disabled,c_active) VALUES (2,'200','تایید شده',2,0,1);
INSERT INTO t_categoryElement (id,c_code,c_name,c_category,c_disabled,c_active) VALUES (3,'300','بررسی تشده',2,0,1);
INSERT INTO t_categoryElement (id,c_code,c_name,c_category,c_disabled,c_active) VALUES (4,'400','رد شده',2,0,1);
INSERT INTO t_categoryElement (id,c_code,c_name,c_category,c_disabled,c_active) VALUES (5,'500','روزانه',3,0,1);
INSERT INTO t_categoryElement (id,c_code,c_name,c_category,c_disabled,c_active) VALUES (6,'600','ساعتی',3,0,1);