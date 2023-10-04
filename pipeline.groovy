/* groovylint-disable LineLength */
pipeline {
    agent any
    
    stages{
      // User Input
        stage('Approval Needed') {
            input{
                    message "Do you want to remove the repo?"
                }
          steps{
            sh "rm -rf eks-cluster"
          }
        }

      // Cloning Git
        stage('Cloning Git'){
          steps{
            sh "git clone https://github.com/Sarthak044/eks-cluster.git"
          }
        }
    
        //Deploying Cluster
        stage('Deploying Cluster'){
          steps{
            script{
              sh 'aws cloudformation create-stack --stack-name eks-test --template-body file:///var/lib/jenkins/workspace/cluster/eks-cluster/eks-cluster-deploy.yaml --capabilities CAPABILITY_NAMED_IAM'
              sleep(700)
            }
          }
        }
        //Deploying Nodes
        stage('Deploying Nodes') {
          steps{
            script{
              sh 'aws cloudformation create-stack --stack-name node-grup --template-body file:///var/lib/jenkins/workspace/cluster/eks-cluster/nodes-eks.yaml --capabilities CAPABILITY_NAMED_IAM'
            }
          }
        }
    }
}
