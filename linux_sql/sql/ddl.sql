--psql -h localhost -U postgres -W -d host_agent

CREATE TABLE PUBLIC.host_info
  (
     id               SERIAL NOT NULL,
     hostname         VARCHAR UNIQUE NOT NULL,
		 cpu_number       INTEGER NOT NULL,
		 cpu_architecture VARCHAR NOT NULL,
		 cpu_model        VARCHAR NOT NULL,
		 cpu_mhz          DECIMAL NOT NULL,
		 L2_cache         INTEGER NOT NULL,
		 total_mem        INTEGER NOT NULL,
     "timestamp"      TIMESTAMP NOT NULL,
     PRIMARY KEY (id)

  );
--INSERT INTO host_info(id, hostname, cpu_number, cpu_architecture, cpumodel, cpu_mhz, L2_cache, total_mem, "timestamp")
CREATE TABLE PUBLIC.host_usage
  (
     "timestamp"    TIMESTAMP NOT NULL,
     host_id        SERIAL NOT NULL ,
		memory_free     INTEGER NOT NULL,
		cpu_idle        INTEGER NOT NULL,
		cpu_kernel      INTEGER NOT NULL,
		disk_io         INTEGER NOT NULL,
		disk_available  INTEGER NOT NULL,
		CONSTRAINT fk_id
		FOREIGN KEY(host_id)
		REFERENCES host_info(id)

  );
  --DROP TABLE host_usage;
  --DROP TABLE host_info;

--INSERT INTO host_usage("timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)