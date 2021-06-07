create
or replace function fix_missing_date_started() returns void as $$
	declare
rec record;
begin
for rec in
select p.id, min(date_visit) date_visit
from patient p
         join pharmacy o on p.id = patient_id,
     jsonb_array_elements(lines) with ordinality a(l)
where date_started is null
  and p.archived = false
  and o.archived
    = false
  and (l ->>'regimen_type_id')::int in (1, 2, 3, 4, 14)
group by 1
    loop
update patient
set date_started  = rec.date_visit,
    last_modified = current_timestamp
where id = rec.id;
end loop;
end;
$$
language plpgsql;

select fix_missing_date_started();
