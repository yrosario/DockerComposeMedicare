BEGIN
    IF NOT EXISTS (SELECT * FROM role
		WHERE role.name = 'User');
   BEGIN
	   INSERT INTO role(id, name)
	  VALUES(1,'User');
   END
END
