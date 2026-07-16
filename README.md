[![Automatic version updates](https://github.com/zopencommunity/ripgrepport/actions/workflows/bump.yml/badge.svg)](https://github.com/ZOSOpenTools/ripgrepport/actions/workflows/bump.yml)

# ripgrep (rg)

Recursively search directories for a regex pattern. Faster than grep, respects
`.gitignore`, and written in Rust.

Upstream project: https://github.com/BurntSushi/ripgrep

# Installation and Usage

Use the zopen package manager ([QuickStart Guide](https://zopen.community/#/Guides/QuickStart)) to install:
```bash
zopen install ripgrep
```

Then search with `rg`:
```bash
rg <pattern> [path]
rg 'fn main'         # search for 'fn main' in current directory
rg -t rust 'unsafe'  # search only Rust files
rg -l 'TODO'         # list files containing TODO
```

# How this port was built

`ripgrep` is written in Rust. Because the Rust toolchain is not yet natively
available on z/OS, this port was **cross-compiled** on a Linux-on-Z (LoP) host
using an IBM-internal Rust cross-compilation toolchain targeting `s390x-ibm-zos`.

The cross-compilation infrastructure and all required patches to upstream Rust
crates are maintained at:

  https://github.ibm.com/compiler/rust-scripts (branch `itodorov/zos-cross-compile-setup`)

The patches cover the following crates that required z/OS-specific fixes:
- `libc` — z/OS `struct dirent`, `termios`, `fcntl` constants, missing symbols
- `rustix` — z/OS `errno` codes, termios flags, missing syscalls
- `nix` — z/OS signal numbers, `errno` variants, `unistd` APIs
- `memmap2` — no-op C stubs for `madvise`/`mlock`/`munlock` (absent on z/OS)
- `pcre2-sys` — prebuilt PCRE2 library bypass (JIT source build unsupported on z/OS)

The resulting binary is statically linked against the Rust standard library
and dynamically linked against the z/OS system libraries (`libc.a`, `libzoslib.so`).

# Troubleshooting

- Files must be ASCII-tagged for rg to search them correctly. Use `chtag -tc ISO8859-1 <file>` or `chtag -Rtc ISO8859-1 <dir>`.
- Binary files are skipped automatically; use `-a` / `--text` to search them anyway.

# Contributing
Contributions are welcome! Please follow the [zopen contribution guidelines](https://github.com/zopencommunity/meta/blob/main/CONTRIBUTING.md).
