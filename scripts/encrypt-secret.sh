#!/bin/bash
set -e

encrypt() {
  local passphrase="$1"
  local input_file="$2"
  local output_file="$3"
  gpg --batch --yes --passphrase="$passphrase" --cipher-algo AES256 --symmetric --output "$output_file" "$input_file"
}

key=""
args=()

while [[ $# -gt 0 ]]; do
  while getopts "k:" opt; do
    case "$opt" in
      k) key="$OPTARG" ;;
      *) echo "Invalid option" >&2; exit 1 ;;
    esac
  done
  shift "$((OPTIND - 1))"
  OPTIND=1
  args+=("$1")
  shift
done

if [[ -z "$key" ]]; then
  echo "Missing encrypt key" >&2
  exit 1
fi

if [[ ${#args[@]} -ne 2 ]]; then
  echo "Incorrect number of arguments" >&2
  exit 1
fi

encrypt "$key" "${args[0]}" "${args[1]}"
