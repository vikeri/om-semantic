## Om-Semantic

[Semantic UI](https://github.com/semantic-org/semantic-ui/) components built with [Om](https://github.com/omcljs/om)

Still very much in Alpha, use at own risk.

Examples: [vikeri.github.io/om-semantic](http://vikeri.github.io/om-semantic/)

## Available components

- Dropdown `dropdown.cljs`

## TODO

- Make dropdown tabbable, and to be able to select with up/down keys

## Add it as Leiningen dependency

![Clojars Project](http://clojars.org/om-semantic/latest-version.svg)

## Try it out

```sh
git clone https://github.com/vikeri/om-semantic.git
cd om-semantic
lein cljsbuild once dropdown
```
Open `index.html` in folder `examples/dropdown`

## Testing

To test your changes you need to install PhantomJS and run:

```sh
lein cljsbuild auto tests
```

## License

Licensed under the EPL
