alias code='codium'

parse_git_branch() {
     git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/ (\1)/'
}

append_name() {
    if [ -n "$name" ]; then
        echo " [shell:$name]"
    fi
}

export PS1="\u@\h \[\033[32m\]\w\[\033[33m\]\$(parse_git_branch)\$(append_name)\[\033[00m\] $ "
