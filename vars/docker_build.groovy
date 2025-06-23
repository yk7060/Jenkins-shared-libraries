def call(String ImageTag, String DockerHubUser) {
    sh "docker build -t ${DockerHubUser}/${env.ProjectName}:${ImageTag} ."
}
