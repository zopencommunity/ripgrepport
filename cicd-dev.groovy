node('linux') {
  stage ('Poll') {
    checkout([
      $class: 'GitSCM', branches: [[name: '*/main']], extensions: [],
      userRemoteConfigs: [[url: 'https://github.com/zopencommunity/ripgrepport.git']]])
  }
  stage('Build') {
    build job: 'Port-Pipeline', parameters: [
      string(name: 'PORT_GITHUB_REPO', value: 'https://github.com/zopencommunity/ripgrepport.git'),
      string(name: 'PORT_DESCRIPTION', value: 'Recursively search directories for a regex pattern'),
      string(name: 'BUILD_LINE', value: 'DEV')
    ]
  }
}
