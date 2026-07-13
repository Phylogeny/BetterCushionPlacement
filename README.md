<div align="center">
<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/logo.png?raw=true" alt="Project Logo">

<h1>Better Cushion Placement</h1>
</div>

## Cushions can be placed as follows:

| Click: block                                                                                                                            | Click: cushion                                                                                                                                               |
|-----------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/block_grid.png?raw=true" alt="Block Grid"> | <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/cushion_stack.png?raw=true" alt="Vertical Pixel Grid Snap: On"> |

### Holding <code>Shift</code>
- Place a cushion horizontally centered on the closest vertex of the block grid.
- Stack a held cushion on one in the world by interacting with it (if it will have block support).

---

<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/pixel_grid.png?raw=true" alt="Pixel Grid">

### Holding <code>Control</code>
- Place a cushion horizontally centered on the closest vertex of the pixel grid.

---

<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/off_grid.png?raw=true" alt="Off-Grid">

### Holding <code>Shift</code> + <code>Control</code>
- Place a cushion horizontally centered exactly where clicked, completely off-grid.

---

<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/block_center.png?raw=true" alt="Block Center">

### Holding neither (<em>Vanilla behavior</em>)
- Place a cushion horizontally centered on the closest block grid tile center.

---
Vanilla behavior is currently to vertically position cushions exactly where clicked, but this will likely be changed to vertically snapping them to the pixel grid.

| Snapping: On                                                                                                                                                               | Snapping: Off                                                                                                                                                                |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/vertical_pixel_grid_snap_on.png?raw=true" alt="Vertical Pixel Grid Snap: On"> | <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/vertical_pixel_grid_snap_off.png?raw=true" alt="Vertical Pixel Grid Snap: Off"> |

### Vertical pixel grid snapping of cushions can be enabled/disabled by a game rule:
- ID
  - `bettercushionplacement:snap_cushion_elevation_to_pixel_grid`
- Name
  - Snap Cushion Elevation To Pixel Grid
- Description
  - Causes cushions placed on the inner walls of blocks like cauldrons to snap to the nearest pixel.
- Default
  - Off