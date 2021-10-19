#!/bin/bash
#captures CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#checks if there are any missing arguments
if [ $# -ne 5 ]; then
  echo 'Invalid amount of arguments'
  exit 1
fi

#saves machine information
hostname=$(hostname -f)
lscpu_out=$(lscpu)
memory_InfoTotal=$(cat /proc/meminfo)

#retrieves the specified machine's specs
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model name:" | awk '{print $3,$4,$5,$6,$7}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
L2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print ($3+0)}' | xargs)
total_mem=$(echo "$memory_InfoTotal"  | egrep "^MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(date +"%F %T")

#PSQL command to insert data into host_info table
insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp) VALUES('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$L2_cache', '$total_mem', '$timestamp');"

#set up env var for the psql command
export PGPASSWORD=$psql_password

#Inserts data into the host_info table
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
