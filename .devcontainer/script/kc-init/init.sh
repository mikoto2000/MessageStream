kcadm.sh config credentials \
  --server http://keycloak:8080 \
  --realm master \
  --user ${KEYCLOAK_ADMIN} \
  --password ${KEYCLOAK_ADMIN_PASSWORD}
kcadm.sh create realms \
  -s realm="${REALM}" \
  -s displayName="${REALM}" \
  -s enabled=true \
  -s registrationAllowed=false \
  -s resetPasswordAllowed=true
kcadm.sh create clients \
  -r "${REALM}" \
  -s clientId="${CLIENT_ID}" \
  -s id="${CLIENT_ID}" \
  -s redirectUris='["*"]' \
  -s webOrigins='["*"]' \
  -s directAccessGrantsEnabled=true \
  -s secret="${CLIENT_SECRET}"
kcadm.sh update clients/"${CLIENT_ID}" \
  -r "${REALM}" \
  -s 'attributes={"post.logout.redirect.uris" : "*"}' \

CLIENT_SCOPE_ROLES_ID=$(kcadm.sh get client-scopes -r "${REALM}" | jq -r '.[] | select( .name == "roles").id')
MAPPER_ID=$(kcadm.sh get -r "${REALM}" client-scopes/${CLIENT_SCOPE_ROLES_ID}/protocol-mappers/models | jq -r '.[] | select(.name == "realm roles").id')
kcadm.sh update -r "${REALM}" client-scopes/${CLIENT_SCOPE_ROLES_ID}/protocol-mappers/models/${MAPPER_ID} -s 'config={
  "multivalued" : "true",
  "userinfo.token.claim" : "true",
  "id.token.claim" : "true",
  "access.token.claim" : "true",
  "claim.name" : "roles",
  "jsonType.label" : "String"
  }'
kcadm.sh create users -r "${REALM}" -s username=test -s email="test@test.com" -s enabled=true -s emailVerified=true
kcadm.sh set-password -r "${REALM}" --username test --new-password test
kcadm.sh create users -r "${REALM}" -s username=normal -s email="normal@test.com" -s enabled=true -s emailVerified=true
kcadm.sh set-password -r "${REALM}" --username normal --new-password test
kcadm.sh create roles -r "${REALM}" -s name=admin
kcadm.sh add-roles -r "${REALM}" --uusername test --rolename admin

