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

create_user "Sebastian" "Foo" "sebastian@example.com"
create_user "Mark" "Bar" "mark@example.com"
create_user "John" "Doe" "john@example.com"
