# Brighton Tide Post

Posts the latest Brighton Tide low tides to social media. Intended to replace [brighton-tide-twitter](https://github.com/d6y/brightontide) eventually.

Currently implemented: 

- [x] Mastodon
- [x] Twitter

See also:

- [Brighton Tide on Amazon Alexa](https://www.amazon.co.uk/dp/B078KLX513)
- [Brighton Tide on Mastodon](https://mastodon.social/web/accounts/482847)
- [Brighton Tide on Twitter](https://twitter.com/brightontide)
- [Brighton Tide on Google Home](https://assistant.google.com/services/a/uid/000000139c4246a6?hl=en-GB)
- A partial implementation [in Rust](https://gitlab.com/d6y/brightontide-post).
- Data gathering [via close source](https://gitlab.com/d6y/brighton-tide-gather).
- Google Home code is [closed source](https://gitlab.com/d6y/brighton-tide-home-firebase).
- Alexa code is [closed source](https://gitlab.com/d6y/brighton-tide-skill).

# Running this code

The data is stored in DynamoDB. You'll need a Dynamo table and tokens set to read from the table.
The table format is as per the `TideRow` case class in this code base.

```
MASTODON_ACCESS_TOKEN=aaa AWS_ACCESS_KEY_ID=bbb AWS_SECRET_ACCESS_KEY=ccc sbt
sbt> run
```

...without the `MASTODON_ACCESS_TOKEN` the post text will be printed only.

To include a post to Twitter, run with the environment variables of:
`TWIT_CONSUMER_KEY`, `TWIT_TOKEN_VALUE`, `TWIT_CONSUMER_SECRET`, `TWIT_ACCESS_TOKEN`.

# Build and run using Docker

```
docker build -t tide-post .
docker run -it --rm --name running-post tide-post
```

