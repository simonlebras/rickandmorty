#!/bin/bash
set -e

removeVersionAttribute() {
    local file="$1"
    if [[ "$(uname)" == "Darwin" ]]; then
        sed -i '' 's/\(trusted-key.*\)version="[^"]*"/\1/' "$file"
    else
        sed -i 's/\(trusted-key.*\)version="[^"]*"/\1/' "$file"
    fi
}

updateVerificationMetadata() {
    local projects=("" "build-logic") # Empty string for root project

    for project in "${projects[@]}"; do
        local gradle_args="--write-verification-metadata pgp,sha256 --export-keys help"
        local project_path="gradle/verification-metadata.xml"

        if [[ -n "$project" ]]; then
            gradle_args="$gradle_args -p $project"
            project_path="$project/$project_path"
        fi

        ./gradlew "$gradle_args"
        removeVersionAttribute "$project_path"
    done
}

updateVerificationMetadata

echo "Done. Please check that these changes look correct (\`git diff\`)"
