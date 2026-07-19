# Changelog of Better Cushion Placement

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/2.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Game rule `Cushions Support Each Other`:
  - Description: Instead of only being supported by blocks, cushions can also be supported by other cushions, allowing direct stacking.
  - Default: `Disabled`

- Game rule features (`Cushions Support Each Other` and `Snap Cushion Elevation To Pixel Grid`) can be set to always enabled or always disabled in the `bettercushionplacement-gamerules.properties` file in the `config` folder.
  - If set to always enabled/disabled the corresponding rule will not exist, but the feature will.
  - If set to always disabled, the corresponding mixin that provides the feature will not be applied.
    - This means that even the negligible performance cost of the method call the mixin results in will be prevented.

### Fixed

- Fixed bug where a player's hand would swing even when failing to stack a cushion on another cushion.

## [0.2.0] - 2026-07-17

**Minecraft:** 26.3-snapshot-4

**Loaders:** Fabric

### Changed

- Updated to 26.3-snapshot-4
- Game rule changed:
  - Old: `Snap Cushion Elevation To Pixel Grid`
    - Description: Causes cushion elevation to snap to the nearest pixel, preventing sub-pixel placement on the inner walls of cauldrons, composters, and hoppers.
    - Default: `Enabled`
  - New: `Allow Inner Wall Cushion Placement`
    - Description: Ignores `#cushion_uses_collision_shape` block tags, thus allowing sub-pixel cushion placement on the inner walls of cauldrons, composters, and hoppers without a data pack.
    - Default: `Disabled`

### Fixed

- Made mixin method and variable targeting more robust.

## [0.1.0] - 2026-07-17

**Minecraft:** 26.3-snapshot-3

**Loaders:** Fabric

### Added

- Initial working state.