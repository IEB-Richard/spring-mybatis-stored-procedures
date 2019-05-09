USE `mybatis`;

# the first procedure
DROP PROCEDURE IF EXISTS `select_user_by_id`;
DELIMITER $$
CREATE PROCEDURE `select_user_by_id`(
	in userId bigint,
    out userName varchar(50),
    out userPassword varchar(50),
    out userEmail varchar(50),
    out userInfo text,
    out headImg BLOB,
    out createTime DATETIME
)
BEGIN
	select user_name,
		   user_password,
           user_email,
           user_info,
           head_img,
           create_time
	 into  userName,
		   userPassword,
           userEmail,
           userInfo,
           headImg,
           createTime
	from sys_user
    where id = userId;
END$$
DELIMITER ;

# the second stored procedure
DROP PROCEDURE IF EXISTS `select_user_page`;
DELIMITER ;;
CREATE PROCEDURE `select_user_page`(
	IN userName VARCHAR(50),
    IN _offset BIGINT,
    IN _limit  BIGINT,
    OUT total BIGINT
)
BEGIN
    SELECT COUNT(*) INTO total
		FROM sys_user
        WHERE user_name LIKE CONCAT('%', userName, '%');
        
    SELECT *
		FROM sys_user
        WHERE user_name LIKE CONCAT('%', userName, '%')
        LIMIT _OFFSET, _LIMIT;
END
;;
DELIMITER ;

# the third stored procedure
DROP PROCEDURE IF EXISTS `insert_user_and_roles`;
DELIMITER ;;
CREATE PROCEDURE `insert_user_and_roles`(
	OUT userId BIGINT,
	IN userName VARCHAR(50),
	IN userPassword VARCHAR(50),
	IN userEmail VARCHAR(50),
	IN userInfo TEXT,
	IN headImg BLOB,
	OUT createTime DATETIME,
	IN roleIds VARCHAR(200)
)
BEGIN
	# set the current time
	SET createTime = NOW();
    # insert record
	INSERT INTO sys_user(user_name, user_password, user_email, user_info, head_img, create_time)
	VALUES(userName, userPassword, userEmail, userInfo, headImg, createTime);
    # get the auto increment id
    SELECT LAST_INSERT_ID() INTO userId;
    # save the relationship table between user and role
    SET roleIds = CONCAT(',', roleIds, ',');
    INSERT INTO sys_user_role(user_id, role_id) 
    SELECT userId, id FROM sys_role 
    WHERE INSTR(roleIds, CONCAT(',', id, ',')) > 0;
END
;;
DELIMITER ;

# the forth stored procedure
DROP PROCEDURE IF EXISTS `delete_user_by_id`;
DELIMITER ;;
CREATE PROCEDURE `delete_user_id`(IN userId BIGINT)
BEGIN
DELETE FROM sys_user_role WHERE user_id = userId;
DELETE FROM sys_user WHERE id = userId;
END
;;
DELIMITER ;
