## Collaborator Guide
First, you need to clone the repository.
```shell script
git clone https://github.com/aliyun/alibabacloud-microservice-demo.git
```
Some of these local configuration files are encrypted using [git-crypt](https://github.com/AGWA/git-crypt).

If you need to use these profiles, please refer to the following instructions.

## Install git-crypt
[Install Guide](https://github.com/AGWA/git-crypt/blob/master/INSTALL.md)
### MAC OS
```shell script
brew install git-crypt
```
### Linux
```shell script
# CentOS
yum install gcc-c++
yum install openssl-devel

# Ubuntu
apt-get install g++
apt-get install libssl-dev

# All
git clone https://github.com/AGWA/git-crypt
cd ./git-crypt
make
make install
```

### Windows
Download exe file from [git-crypt-windows](https://github.com/oholovko/git-crypt-windows/releases/tag/1.0.35)

And then copy this file to `/{git-path}/Git/cmd/`

## Get git-crypt-key
Contact @IcedSoul(Xiaofeng) to get `git-crypt-key` file.

## Unlock file
To decrypt the file, execute the following command in the warehouse directory:
```shell script
git-crypt unlock /path/git-crypt-key
```


Then you can pull and push normally, and the encrypted file will be decrypted and encrypted automatically during pull and push.