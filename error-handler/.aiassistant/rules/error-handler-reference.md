Handling - Quick Reference

**The essential rules for idiomatic Go error handling.**

## The Three Golden Rules

1. **Errors are values** - Return `error` as last value, never use panic for control flow
2. **Add context, preserve chain** - Use `fmt.Errorf("context: %w", err)` to wrap
3. **Log at boundaries only** - HTTP handlers, CLI commands, workers - NOT in service/repo layers

## Basic Pattern

```go
// ✅ The standard pattern
func DoWork(id string) (*Result, error) {
	data, err := fetch(id)
	if err != nil {
		return nil, fmt.Errorf("fetching data for %s: %w", id, err)
	}

	result, err := process(data)
	if err != nil {
		return nil, fmt.Errorf("processing data: %w", err)
	}

	return result, nil
}
```

## Check Errors

```go
// ✅ Use errors.Is for sentinel errors
if errors.Is(err, ErrNotFound) { ... }

// ✅ Use errors.As for custom types
var validationErr *ValidationError
if errors.As(err, &validationErr) { ... }

// ❌ Never compare strings
if err.Error() == "not found" { ... } // NO!
```

## When to Panic

Only for:

- Programmer errors (impossible states)
- Init failures (`func main()`)

```go
// ✅ Acceptable
func main() {
	cfg, err := loadConfig()
	if err != nil {
		panic(err)
	}
}

// ❌ Never
func SaveUser(u *User) error {
	if err := db.Save(u); err != nil {
		panic(err) // NO!
	}
}
```

## Quick Checklist

Before committing code, verify:

- [ ] Every error is handled or explicitly ignored with comment
- [ ] Errors wrapped with `%w` include relevant context
- [ ] No logging in service/repository layers
- [ ] Using `errors.Is`/`errors.As` not string comparison
- [ ] Early returns instead of nested ifs

## Need More?

For specific scenarios, see:

- **Sentinel errors** - [sentinel-errors.md](./errors/sentinel-errors.md)

[//]: # (- **Custom types** - [CUSTOM_TYPES.md](./errors/CUSTOM_TYPES.md))
[//]: # (- **HTTP handlers** - [HTTP_HANDLERS.md](./errors/HTTP_HANDLERS.md))
[//]: # (- **Concurrency** - [CONCURRENCY.md](./errors/CONCURRENCY.md))
[//]: # (- **Testing** - [TESTING.md](./errors/TESTING.md))

---

**Remember:** When in doubt, choose the simplest approach that explicitly handles errors.
