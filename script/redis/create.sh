#!/bin/bash

## Redis create script
## Version: Redis 6.2.0
## Usage: 1. `vim /etc/redis.conf`
##            uncomment line `aclfile /etc/redis/users.acl`
##            and modify to `aclfile /var/lib/redis/users.acl`
##            (this step only execute once)
##        2. `touch /var/lib/redis/users.acl && chown redis:redis /var/lib/redis/users.acl`
##            (this step only execute once)
##        3. `systemctl restart redis`(this step only execute once)
##        4. `bash create.sh`

echo "Add user"
echo "ACL SETUSER dcp6pX0 on >oAnNpFg1IidswJ5c5ZZHoEYLqD93Ufvq ~fr:* +@all" | redis-cli

echo "Save config"
echo "ACL SAVE" | redis-cli
