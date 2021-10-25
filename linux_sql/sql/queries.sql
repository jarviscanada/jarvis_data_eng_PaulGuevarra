--SQL QUERIES
--1. Query to group hosts by CPU number and sort by memory size in descending order
select cpu_number, id, total_mem
from  host_info
group by cpu_number, id
order by total_mem desc;


--2.a) Function to get round every timestamp to the nearest 5 minute interval
CREATE OR REPLACE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

--2.b) Query to get the average memory used (in percentage) over a 5 minute interval for each host

SELECT u.host_id, round5(u.timestamp)as rts, AVG((i.total_mem/1000)-memory_free)::int as memory_used
FROM host_usage u
inner join host_info i
on u.host_id =i.id
group by u.host_id,rts
order by rts;

--3.Query to detect all host failures (cron job should enter a new entry every minute)

SELECT u.host_id, round5(u.timestamp)as ts, count(round5(u.timestamp)) as num_data_points
FROM host_usage u
inner join host_info i
on u.host_id =i.id
group by u.host_id,ts
having count(round5(u.timestamp))<5
order by ts;