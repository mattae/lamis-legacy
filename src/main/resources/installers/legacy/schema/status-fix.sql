CREATE
OR REPLACE FUNCTION fix_status() RETURNS VOID AS $$
        DECLARE
t VARCHAR;
          rec
RECORD;
BEGIN
FOR rec IN
select date_status, patient_id, count(*)
from status_history
where auto = true
group by 1, 2
having count(*) > 1
    LOOP
DELETE
FROM status_history
WHERE id IN (SELECT id
             FROM status_history
             WHERE auto = true
               AND patient_id = rec.patient_id
               AND date_status = rec.date_status
             ORDER BY id
             OFFSET 1);
END LOOP;
END;
      $$
LANGUAGE PLPGSQL;

      CREATE
OR REPLACE FUNCTION fix_iit2() RETURNS VOID AS $$
          DECLARE
t VARCHAR;
            rec
RECORD;
BEGIN
FOR rec IN
SELECT *
FROM (
         SELECT id,
                patient_id,
                date_status,
                status      curr,
                LAG(status) OVER(partition by patient_id ORDER BY date_status asc) prev
         FROM status_history
         where archived = false
     ) l
where curr = prev
  and curr = 'LOST_TO_FOLLOWUP' LOOP
              EXECUTE FORMAT('UPDATE status_history SET last_modified = current_timestamp, archived = true where id = %s', rec.id);
END LOOP;
END;
      $$
LANGUAGE PLPGSQL;

      CREATE
OR REPLACE FUNCTION fix_iit() RETURNS VOID AS $$
          DECLARE
t VARCHAR;
            rec
RECORD;
BEGIN
FOR rec IN
SELECT *
FROM (
         SELECT id,
                patient_id,
                date_status,
                status      curr,
                LAG(status) OVER(partition by patient_id ORDER BY date_status asc) prev
         FROM status_history
         where archived = false
     ) l
where curr = 'LOST_TO_FOLLOWUP'
  and prev in ('PREVIOUSLY_UNDOCUMENTED_TRANSFER_CONFIRMED',
               'PRE_ART_TRANSFER_OUT', 'KNOWN_DEATH', 'STOPPED_TREATMENT', 'ART_TRANSFER_OUT',
               'HIV_EXPOSED_STATUS_UNKNOWN', 'PRE_ART_TRANSFER_IN', 'HIV_NEGATIVE',
               'PREVIOUSLY_UNDOCUMENTED_TRANSFER_CONFIRMED')
    LOOP
              EXECUTE FORMAT('UPDATE status_history SET last_modified = current_timestamp, archived = true where id = %s', rec.id);
END LOOP;
END;
      $$
LANGUAGE PLPGSQL;

select fix_status();
select fix_iit();
select fix_iit2();
