param(
    [string]$Version = "2.0.0"
)

$ErrorActionPreference = "Stop"

function Require-Command {
    param([string]$Name)

    if (-not (Get-Command $Name -ErrorAction SilentlyContinue)) {
        throw "$Name was not found. Please install a JDK that includes $Name."
    }
}

function Remove-PathInsideProject {
    param(
        [string]$PathToRemove,
        [string]$ProjectRoot
    )

    $fullPath = [System.IO.Path]::GetFullPath($PathToRemove)
    $fullRoot = [System.IO.Path]::GetFullPath($ProjectRoot)

    if (-not $fullPath.StartsWith($fullRoot, [System.StringComparison]::OrdinalIgnoreCase)) {
        throw "Refusing to remove a path outside the project: $fullPath"
    }

    if (Test-Path -LiteralPath $fullPath) {
        Remove-Item -LiteralPath $fullPath -Recurse -Force
    }
}

$projectRoot = [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot ".."))
Set-Location $projectRoot

Require-Command "javac"
Require-Command "jar"
Require-Command "jpackage"

$buildRoot = Join-Path $projectRoot "build\package"
$classesDir = Join-Path $buildRoot "classes"
$jarDir = Join-Path $buildRoot "jars"
$jarFile = Join-Path $jarDir "AircraftWar.jar"
$distRoot = Join-Path $projectRoot "dist"
$windowsDist = Join-Path $distRoot "windows"
$appDir = Join-Path $windowsDist "AircraftWar"
$zipFile = Join-Path $distRoot "AircraftWar-v$Version-windows.zip"

Remove-PathInsideProject $buildRoot $projectRoot
Remove-PathInsideProject $windowsDist $projectRoot
if (Test-Path -LiteralPath $zipFile) {
    Remove-Item -LiteralPath $zipFile -Force
}

New-Item -ItemType Directory -Path $classesDir | Out-Null
New-Item -ItemType Directory -Path $jarDir | Out-Null
New-Item -ItemType Directory -Path $distRoot | Out-Null

$sourcesFile = Join-Path $buildRoot "sources.txt"
Get-ChildItem -Recurse -File -Path "src\aircraftwar" -Filter "*.java" |
    ForEach-Object FullName |
    Set-Content -Encoding ASCII $sourcesFile

Write-Host "Compiling source code..."
javac -encoding UTF-8 -d $classesDir "@$sourcesFile"

Write-Host "Copying game resources..."
Copy-Item -Path "src\images" -Destination (Join-Path $classesDir "images") -Recurse
Copy-Item -Path "src\videos" -Destination (Join-Path $classesDir "videos") -Recurse

Write-Host "Creating application jar..."
jar --create --file $jarFile --main-class aircraftwar.application.Main -C $classesDir .

Write-Host "Creating Windows application image..."
jpackage `
    --type app-image `
    --name AircraftWar `
    --app-version $Version `
    --input $jarDir `
    --main-jar AircraftWar.jar `
    --main-class aircraftwar.application.Main `
    --dest $windowsDist `
    --java-options "-Dfile.encoding=UTF-8"

$playerReadme = Join-Path $appDir "README_FOR_PLAYERS.txt"
@"
Aircraft War v$Version

How to play:
1. Double-click AircraftWar.exe.
2. Choose Chinese or English on the start screen.
3. Choose a difficulty mode.
4. Move the mouse to control the hero aircraft.

Java is already included in this package. You do not need to install Java.

If Windows shows an unknown publisher warning, choose More info and Run anyway
only if you downloaded this package from the official GitHub release page.
"@ | Set-Content -Encoding UTF8 $playerReadme

Write-Host "Creating zip package..."
Compress-Archive -Path $appDir -DestinationPath $zipFile -Force

Write-Host ""
Write-Host "Done."
Write-Host "Release package: $zipFile"
