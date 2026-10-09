param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]] $GradleArguments
)

$ErrorActionPreference = 'Stop'
$projectDirectory = Split-Path -Parent $PSScriptRoot
$javaCandidates = @()
if ($env:JAVA_HOME) {
    $javaCandidates += $env:JAVA_HOME
}
foreach ($javaRoot in @(
    (Join-Path $env:USERPROFILE '.jdks'),
    (Join-Path $env:ProgramFiles 'Eclipse Adoptium')
)) {
    if (Test-Path -LiteralPath $javaRoot) {
        $javaCandidates += Get-ChildItem -LiteralPath $javaRoot -Directory |
            Select-Object -ExpandProperty FullName
    }
}
$java21Directory = $javaCandidates | Where-Object {
    $releaseFile = Join-Path $_ 'release'
    (Test-Path -LiteralPath $releaseFile) -and
        ((Get-Content -LiteralPath $releaseFile) -match '^JAVA_VERSION="21[."]')
} | Select-Object -First 1
if (-not $java21Directory) {
    throw 'Java 21 is required. Install a JDK 21 under .jdks or set JAVA_HOME to it.'
}

$env:JAVA_HOME = $java21Directory
$env:GRADLE_USER_HOME = Join-Path $projectDirectory '.gradle-user-home'
Push-Location $projectDirectory
try {
    & (Join-Path $projectDirectory 'gradlew.bat') @GradleArguments --no-daemon
    $gradleExitCode = $LASTEXITCODE
} finally {
    Pop-Location
}
exit $gradleExitCode
