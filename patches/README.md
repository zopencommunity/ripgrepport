# Patches for ripgrep on z/OS (s390x-ibm-zos)

## Overview

`ripgrep 15.1.0` was ported to z/OS (`s390x-ibm-zos`) with PCRE2 support.

Key crates patched:
1. **`pcre2-sys`**: Configured to link against z/OS system PCRE2 or build with z/OS C flags (`-qascii`).
2. **`memmap2`**: Stubs added for `posix_madvise` and `msync` which are not present in standard z/OS C runtime.
3. **`grep-pcre2`**: Feature selection and runtime bindings for z/OS.
