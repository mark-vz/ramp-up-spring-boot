#!/bin/sh

function create_user () {
    local firstname="${1}"
    local lastname="${2}"
    local email_address="${3}"

    local url="localhost:8080/api/users"
    local body="{\"firstName\": \"${firstname}\", \"lastName\": \"${lastname}\", \"emailAddress\": \"${email_address}\"}"
    local body_len=${#body}

    curl \
        -X POST \
        -H "Content-type: application/json" \
        -H "Content-Length: ${body_len}" \
        -d "${body}" \
        -w "%{http_code}\n" \
        "${url}"
}

function create_addresses () {
    local street="${1}"
    local zipcode="${2}"
    local city="${3}"
    local user_id="${4}"

    local url="localhost:8080/api/addresses"
    local body="{\"street\": \"${street}\", \"zipcode\": \"${zipcode}\", \"city\": \"${city}\", \"user\": \"${user_id}\"}"
    local body_len=${#body}

    curl \
        -X POST \
        -H "Content-type: application/json" \
        -H "Content-Length: ${body_len}" \
        -d "${body}" \
        -w "%{http_code}\n" \
        "${url}"
}

# create_user "Sebastian" "Foo" "sebastian@example.com"
# create_user "Mark" "Bar" "mark@example.com"
# create_user "John" "Doe" "john@example.com"

# addresses
create_addresses "Teststrasse 1" "51065" "Koeln" "b659ebb1-d794-459d-9a96-d6d5627a41a7"
create_addresses "Teststrasse 2" "12345" "Koeln" "b659ebb1-d794-459d-9a96-d6d5627a41a7"
create_addresses "Teststrasse 3" "42131" "Koeln" "b659ebb1-d794-459d-9a96-d6d5627a41a7"
create_addresses "An der Teststrasse 1" "12345" "Hamburg" "87c88c7b-ccda-4e70-bad0-44e44ed72072"
create_addresses "Testallee 2" "12341" "Hamburg" "87c88c7b-ccda-4e70-bad0-44e44ed72072"
