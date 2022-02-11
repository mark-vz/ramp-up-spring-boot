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

function create_address_for_user () {
    local email="${1}"
    local street="${2}"
    local zipcode="${3}"
    local city="${4}"

    local url="localhost:8080/api/addresses"
    local body="{\"email\": \"${email}\", \"street\": \"${street}\", \"zipcode\": \"${zipcode}\", \"city\": \"${city}\"}"
    local body_len=${#body}

    curl \
        -X POST \
        -H "Content-type: application/json" \
        -H "Content-Length: ${body_len}" \
        -d "${body}" \
        -w "%{http_code}\n" \
        "${url}"
}

create_user "Sebastian" "Foo" "sebastian@example.com"
create_user "Mark" "Bar" "mark@example.com"
create_user "John" "Doe" "john@example.com"

create_address_for_user "mark@example.com" "Am Foo-Berg 11" "50354" "Huerth"
create_address_for_user "mark@example.com" "Am Bar-Huegel 4711" "50321" "Bruehl"
create_address_for_user "sebastian@example.com" "An der Baz-Ebene 7" "50757" "Koeln"
