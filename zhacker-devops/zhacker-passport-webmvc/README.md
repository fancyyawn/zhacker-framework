Once it is up, this request asks for a token with the "message:read" scope:

```bash
curl reader:secret@localhost:8181/oauth/token -d grant_type=password -d username=subject -d password=password
```

Which will respond with something like:

```json
{
    "access_token":"eyJhbGciOiJSUzI1NiIsI...Fhq4RIVyA4ZAkC7T1aZbKAQ",
    "token_type":"bearer",
    "expires_in":599999999,
    "scope":"message:read",
    "jti":"8a425df7-f4c9-4ca4-be12-0136c3015da0"
}
```

You can also so the same with the `writer` client:

```bash
curl writer:secret@localhost:8181/oauth/token -d grant_type=password -d username=subject -d password=password
```
