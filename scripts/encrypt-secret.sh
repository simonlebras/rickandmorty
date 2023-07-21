#!/bin/bash

encrypt() {
    PASSPHRASE=$1
    INPUT=$2
    OUTPUT=$3
    gpg --batch --yes --passphrase="$PASSPHRASE" --cipher-algo AES256 --symmetric --output "$OUTPUT" "$INPUT"
}

key=""
ARGS=()

while [ $# -gt 0 ]; do
    while getopts k: name; do
        case $name in
        k) key=$OPTARG ;;
        *) exit 1 ;;
        esac
    done
    [ $OPTIND -gt $# ] && break

    shift $((OPTIND - 1))
    OPTIND=1
    ARGS[${#ARGS[*]}]=$1
    shift
done

if [[ -z $key ]]; then
    echo 'Missing encrypt key'
    exit 1
fi

if [ ${#ARGS[@]} -ne 2 ]; then
    echo 'Incorrect number of arguments'
    exit 1
fi

encrypt "$key" "${ARGS[0]}" "${ARGS[1]}"
