--SQL QUERIES
--1. Query to group hosts by CPU number and sort by memory size in descending order
SELECT cpu_number, id, total_mem
FROM  host_info
GROUP BY cpu_number, id
ORDER BY total_mem desc;


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
INNER JOIN host_info i
ON u.host_id =i.id
GROUP BY u.host_id,rts
ORDER BY rts;

--3.Query to detect all host failures (cron job should enter a new entry every minute)

SELECT u.host_id, round5(u.timestamp)as ts, count(round5(u.timestamp)) as num_data_points
FROM host_usage u
INNER JOIN host_info i
ON u.host_id =i.id
GROUP BY u.host_id,ts
HAVING COUNT(round5(u.timestamp))<5
ORDER BY ts;
