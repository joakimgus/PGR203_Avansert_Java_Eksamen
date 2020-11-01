INSERT INTO member_task (member_name, task_title, task_description)
SELECT members.member_name, title, description
FROM members
INNER JOIN tasks
ON
	members.task_id = tasks.id;