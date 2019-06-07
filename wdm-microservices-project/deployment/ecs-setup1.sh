# this script creates an ecs cluster 
# pass key, secret key, and session token as params
export AWS_ACCESS_KEY_ID=$1
export AWS_SECRET_ACCESS_KEY=$2
export AWS_SESSION_TOKEN=$3
export AWS_DEFAULT_REGION=us-east-1

# setting up configuration (access profile and naming of cluster)
ecs-cli configure profile --profile-name default --access-key $AWS_ACCESS_KEY_ID --secret-key $AWS_SECRET_ACCESS_KEY --session-token $AWS_SESSION_TOKEN
ecs-cli configure --cluster wdm-ecs-cluster --default-launch-type FARGATE --region us-east-1 --config-name wdm-ecs-config
ecs-cli configure default --config-name wdm-ecs-config
# create cluster with the configuration (if it does not exist yet)
output=`ecs-cli up`
# parse the id of the vpc the cluster is in
vpc=`echo "$output" | grep -o "vpc-[^ ]*"`
# create a security group for the ecs cluster in the vpc
output2=`aws ec2 create-security-group --group-name "wdm-ecs-security-group" --description "the security group for wdm ecs cluster" --vpc-id "$vpc"`
# parse the security group id
sec_group_id=`echo "$output" | grep -o "sg-[^ \"]*"`
# create access rules for the security group (open ports for all containers)
aws ec2 authorize-security-group-ingress --group-id "$sec_group_id" --protocol tcp --port 8761 --cidr 0.0.0.0/0
aws ec2 authorize-security-group-ingress --group-id "$sec_group_id" --protocol tcp --port 8086 --cidr 0.0.0.0/0
aws ec2 authorize-security-group-ingress --group-id "$sec_group_id" --protocol tcp --port 8080 --cidr 0.0.0.0/0
aws ec2 authorize-security-group-ingress --group-id "$sec_group_id" --protocol tcp --port 8081 --cidr 0.0.0.0/0
aws ec2 authorize-security-group-ingress --group-id "$sec_group_id" --protocol tcp --port 8082 --cidr 0.0.0.0/0
aws ec2 authorize-security-group-ingress --group-id "$sec_group_id" --protocol tcp --port 8083 --cidr 0.0.0.0/0
aws ec2 authorize-security-group-ingress --group-id "$sec_group_id" --protocol tcp --port 8888 --cidr 0.0.0.0/0
