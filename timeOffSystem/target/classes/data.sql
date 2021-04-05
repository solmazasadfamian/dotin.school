INSERT INTO t_category (c_id,c_name,c_disabled,c_active) VALUES (1,'role',0,1);
INSERT INTO t_category (c_id,c_name,c_disabled,c_active) VALUES (2,'leaveStatus',0,1);
INSERT INTO t_categoryElement (c_id,c_code,c_name,c_category,c_disabled,c_active) VALUES (1,'100','developer',1,0,1);
INSERT INTO t_categoryElement (c_id,c_code,c_name,c_category,c_disabled,c_active) VALUES (2,'200','approved',2,0,1);
INSERT INTO t_categoryElement (c_id,c_code,c_name,c_category,c_disabled,c_active) VALUES (3,'300','notapproved',2,0,1);
INSERT INTO t_categoryElement (c_id,c_code,c_name,c_category,c_disabled,c_active) VALUES (4,'400','reject',2,0,1);