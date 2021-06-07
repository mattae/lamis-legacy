CREATE
OR REPLACE FUNCTION fix_biometric_pk() RETURNS VOID AS $$
BEGIN
        IF
NOT EXISTS (SELECT constraint_name FROM information_schema.table_constraints WHERE lower(table_name) = 'biometric' AND constraint_type = 'PRIMARY KEY') THEN
ALTER TABLE biometric
    ADD PRIMARY KEY (id);
END IF;
END
$$
LANGUAGE PLPGSQL;

select fix_biometric_pk();

create
or replace function fix_biometric_template_type() returns void as $$
	declare
rec Record;
		pid
varchar:= '';
		i
numeric:= 1;
		template
varchar;
begin
for rec in
select id, patient_id pid, b.template_type
from biometric b
where iso = true
  and b.template_type = 'Left Middle Finger'
  and archived = false
  and extra - > 'source' ->>'type' = 'mobile'
order by 2, 1
    loop
    if pid <> rec.pid then
    pid = rec.pid;
i
= 1;
end if;
			if
i = 1 then
				template = 'Left Middle Finger';
end if;
			if
i = 2 then
				template = 'Right Middle Finger';
end if;
			if
i = 3 then
				template = 'Left Index Finger';
end if;
			if
i = 4 then
				template = 'Right Index Finger';
end if;
			if
i = 5 then
				template = 'Left Thumb';
end if;
			if
i = 6 then
				template = 'Right Thumb';
end if;
			if
i > 6 then
				EXECUTE FORMAT('update biometric set archived = true, last_modified = current_timestamp where id = ''%s''', rec.id);
else
                EXECUTE FORMAT('update biometric set template_type = ''%s'', last_modified = current_timestamp where id = ''%s''', template, rec.id);
end if;
            i
= i + 1;
end loop;
end
$$
language plpgsql;

select fix_biometric_template_type();

create
or replace function fix_biometric_template_type() returns void as $$
	declare
rec Record;
		pid
varchar:= '';
		i
numeric:= 1;
		template
varchar;
begin
for rec in (
            with p as (
	            select patient_id, template_type, count(*) from biometric where iso = true and
                    template_type = 'Left Middle Finger' and archived = false and extra->'source'->>'type' = 'mobile'
		        group by 1, 2 having count(*) > 1
            )
            select id, patient_id pid, b.template_type from biometric b join p using(patient_id) where iso = true and
                b.template_type = 'Left Middle Finger' and archived = false and extra->'source'->>'type' = 'mobile'
            order by 2, 1)
        loop
            if pid <> rec.pid then
                pid = rec.pid;
                i
= 1;
end if;
			if
i = 1 then
				template = 'Left Middle Finger';
end if;
			if
i = 2 then
				template = 'Right Middle Finger';
end if;
			if
i = 3 then
				template = 'Left Index Finger';
end if;
			if
i = 4 then
				template = 'Right Index Finger';
end if;
			if
i = 5 then
				template = 'Left Thumb';
end if;
			if
i = 6 then
				template = 'Right Thumb';
end if;
			if
i > 6 then
				EXECUTE FORMAT('update biometric set archived = true, last_modified = current_timestamp where id = ''%s''', rec.id);
else
                EXECUTE FORMAT('update biometric set template_type = ''%s'', last_modified = current_timestamp where id = ''%s''', template, rec.id);
end if;
            i
= i + 1;
end loop;
end
$$
language plpgsql;
