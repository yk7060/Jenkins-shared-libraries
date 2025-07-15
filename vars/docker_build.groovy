def call(String ImageTag, String DockerHubUser, ProjectName) {
    sh "docker build -t ${DockerHubUser}/${ProjectName}:${ImageTag} ."
}
