def call(String imageName, String contextDir = '.') {
    sh "docker build -t ${imageName} ${contextDir}"
}
