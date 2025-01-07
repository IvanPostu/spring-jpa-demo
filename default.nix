{ nixpkgs ? import <nixpkgs> {}, jdkVersion ? "21" }:

with nixpkgs;

let 
  jdk17 = pkgs.jdk17;
  jdk21 = pkgs.jdk21;

in
pkgs.stdenv.mkDerivation rec {
  name = "nix-shell";
  buildInputs = [
    jdk17 
    mysql84
    jdk21
    pkgs.dbeaver-bin
    pkgs.nodejs_20
  ];

  JAVA_HOME_17 = "${jdk17}";
  JAVA_HOME_21 = "${jdk21}";
  EXPLICIT_JDK_VERSION = "${jdkVersion}";

  shellHook = ''
    if [ "$EXPLICIT_JDK_VERSION" = "21" ]; then
      echo "Set JAVA_HOME=jdk21"
      export JAVA_HOME="$JAVA_HOME_21"
    elif [ "$EXPLICIT_JDK_VERSION" = "17" ]; then
      echo "Set JAVA_HOME=jdk17"
      export JAVA_HOME="$JAVA_HOME_17"
    fi

    # git config --global user.name "IvanPostu"
    # git config --global user.email ""

    export PATH="$PWD/scripts:$PATH"
    export PATH=$JAVA_HOME/bin:$PATH
    export PROJECT_DIR="$PWD"

    chmod +x $PWD/scripts/*
  '';
}
