#!groovy
/********************************************************************************
***** Description :: This Custom Library is used to Deploy to Remote Server *****
***** Author      :: Mukul Garg                                             *****
***** Date        :: 04/24/2017                                             *****
***** Revision    :: 2.0                                                    *****
********************************************************************************/

import com.sapient.devops.deploy.remote

def call(body) {
  echo "reached remote_deploy"
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
     echo "config remote IP : "+"${config.REMOTE_IP}"
      def remoteDeploy = new remote()
     
      remoteDeploy.setValue("${config.REMOTE_USER}", "${config.REMOTE_IP}", "${config.DEPLOY_PATH}", "${config.SCRIPT}")
      remoteDeploy.deploy()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
