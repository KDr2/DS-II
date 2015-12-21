#!/bin/bash
# -*- mode: sh -*-

DEFAULT_VERSION=5.22.1

[[ -f ~/perl5/perlbrew/etc/bashrc ]] || {
    \curl -L http://install.perlbrew.pl | bash
    source ~/perl5/perlbrew/etc/bashrc
    perlbrew install $DEFAULT_VERSION
    perlbrew alias create $DEFAULT_VERSION default
    perlbrew lib create default@default
}

# No use now, use $DS3_HOME/bin/ds3.rc
function init-bashrc() {
    [[ -f ~/perl5/perlbrew/etc/bashrc ]] &&
        ! grep  "~/perl5/perlbrew/etc/bashrc" ~/.bash_profile && {
            echo -e "\nsource ~/perl5/perlbrew/etc/bashrc" >> ~/.bash_profile
        }
}
