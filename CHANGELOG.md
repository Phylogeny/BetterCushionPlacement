# Changelog of Better Cushion Placement

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/2.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.3.0] - 2026-07-30

**Minecraft:** 26.3-snapshot-6

**Loaders:** Fabric

### Changed

- Updated to 26.3-snapshot-6

### Added

- Client Config `bcp-client.conf`:
  - Configures cushion preview rendering.
- Common Config `bcp-common.conf`:
  - Configures game rule features.
  - Replaces `bettercushionplacement-gamerules.properties`.
- `Mod Menu` support for viewing localized mod name, description, and summary.
  - Only when `Mod Menu` is installed.
- `Yet Another Config Lib` config screen is accessible through `Mod Menu` for changing configs in-game.
  - Only when `Mod Menu` and `Yet Another Config Lib` are both installed.

### Removed

- `bettercushionplacement-gamerules.properties`, as it was replaced by `bcp-common.conf`.

## [1.2.0] - 2026-07-23

**Minecraft:** 26.3-snapshot-5

**Loaders:** Fabric

### Added

- A cushion placement preview box renders where a cushion will place with the color it will have when placed.

## [1.1.0] - 2026-07-21

**Minecraft:** 26.3-snapshot-5

**Loaders:** Fabric

### Added

- Block tag `#normal_cushion_placement` forces the normal center-of-block placement of cushions when placing them against blocks with that tag.
  - By default, this only contains blocks with the `#signs` block tag.

### Fixed

- Fixed mismatched comments in game rule config file.

## [1.0.0] - 2026-07-19

**Minecraft:** 26.3-snapshot-4

**Loaders:** Fabric

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