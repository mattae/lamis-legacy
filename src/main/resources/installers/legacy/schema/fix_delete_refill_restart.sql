create
or replace function fix_delete_refill_restart_status() returns void as $$
	declare
rec Record;
begin
for rec in
select id, patient_id, date_status
from status_history
where archived = false
  and status in ('ART_START', 'ART_RESTART') loop
			if not exists(select * from pharmacy, jsonb_array_elements(lines) with ordinality a(l)
				where cast(jsonb_extract_path_text(l,'regimen_type_id') as integer) in (1, 2, 3, 4, 14) and archived = false
				and date_visit = rec.date_status and patient_id = rec.patient_id) then
				execute format('update status_history set archived = true, last_modified = current_timestamp where id = %s', rec.id);
end if;
end loop;
end;
$$
language plpgsql;

select fix_delete_refill_restart_status();
