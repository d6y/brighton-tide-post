# Brighton Tide Post

Posts the latest Brighton Tide low tides to social media. Intended to replace [brighton-tide-twitter](https://github.com/d6y/brightontide) eventually.

Currently implemented: 

- [x] Mastodon

See also:

- [Brighton Tide on Amazon Alexa](https://www.amazon.co.uk/dp/B078KLX513)
- [Brighton Tide on Mastodon](https://mastodon.social/web/accounts/482847)
- [Brighton Tide on Twitter](https://twitter.com/brightontide)
- [Brighton Tide on Google Home](https://assistant.google.com/services/a/uid/000000139c4246a6?hl=en-GB)

# Running this code

The data is stored in DynamoDB. You'll need a Dynamo table and tokens set to read from the table.
The table format is as per the `TideRow` case class in this code base.


```
MASTODON_ACCESS_TOKEN=aaa AWS_ACCESS_KEY_ID=bbb AWS_SECRET_ACCESS_KEY=ccc sbt
sbt> run
```

...without the `MASTODON_ACCESS_TOKEN` the post text will be printed only.
