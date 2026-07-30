<div align="center">
<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/logo.png?raw=true" alt="Project Logo">

<h1>Better Cushion Placement</h1>
</div>

## Visual Summary

<img hight="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/cushion_placement_small.gif?raw=true" alt="Cushion Placement">

---

## Placement Mechanics

| Click: block                                                                                                                            | Click: cushion                                                                                                                                               |
|-----------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/block_grid.png?raw=true" alt="Block Grid"> | <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/cushion_stack.png?raw=true" alt="Vertical Pixel Grid Snap: On"> |

### Holding `Shift`
- Place a cushion horizontally centered on the closest vertex of the block grid.
- Stack a held cushion on one in the world by interacting with it (if it will have block support).

---

<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/pixel_grid.png?raw=true" alt="Pixel Grid">

### Holding `Control`
- Place a cushion horizontally centered on the closest vertex of the pixel grid.

---

<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/off_grid.png?raw=true" alt="Off-Grid">

### Holding `Shift` + `Control`
- Place a cushion horizontally centered exactly where clicked, completely off-grid.

---

<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/block_center.png?raw=true" alt="Block Center">

### Holding neither (_Vanilla behavior_)
- Place a cushion horizontally centered on the closest block grid tile center.

---

## Placement Preview 

<img hight="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/cushion_placement_preview_small.gif?raw=true" alt="Cushion Placement Preview">

A box renders where a cushion will place with the color it will have when placed.

---

## Block Tags
- ID
  - `#normal_cushion_placement`
- Description
  - Forces the normal center-of-block placement of cushions when placing them against blocks with this tag.
- Default
  - Contains blocks with the `#signs` block tag.

---

## Configs

### Client Config `bcp-client.conf`:

`Placement Preview`: Configures how to render a placement preview box for cushions
- `Enabled`
  - Whether to render a placement preview box for cushions.
- `Line Width Override`
  - If positive, the lines of the box will render with this.
    - If negative, they will render with the default size.
    - If zero, rendering will be skipped.
- `Opacity`
  - The box will render with this alpha value.
    - If zero, rendering will be skipped.
- `Color`
  - The color the box will render as. The color will be:
    - `CUSHION_COLOR`: The dye color the cushion will be when placed.
    - `DYE_COLOR_OVERRIDE`: The dye color specified by the `Dye Color Override` confg.
    - In both cases, the variant of the dye color will be specified by the 'Dye Color Variant' config.
    - `COLOR_OVERRIDE`: The color specified by the `Color Override` confg.
- `Dye Color Override`
  - If the `Color` config is set to `DYE_COLOR_OVERRIDE`, the box will render as this dye color.
    - Otherwise, this will be ignored.
- `Color Override`
  - If the `Color` config is set to `COLOR_OVERRIDE`, the box will render as this color.
    - Otherwise, this will be ignored.
- `Dye Color Variant`
  - If the `Color` config is set to `COLOR_OVERRIDE`, the box will render as this color.
    - Otherwise, this will be ignored.

### Common Config `bcp-common.conf`:
Game rule features (`Cushions Support Each Other` and `Snap Cushion Elevation To Pixel Grid`, _see below_) can be set to always enabled or always disabled in the `bettercushionplacement-gamerules.properties` file in the `config` folder.
- If set to always enabled/disabled the corresponding rule will not exist, but the feature will.
- If set to always disabled, the corresponding mixin that provides the feature will not be applied.
    - This means that even the negligible performance cost of the method call the mixin results in will be prevented.

### Config Screen
- `Yet Another Config Lib` config screen is accessible through `Mod Menu` for changing configs in-game.
  - Only when `Mod Menu` and `Yet Another Config Lib` are both installed.
  - Localized mod name, description, and summary can still be viewed if only `Mod Menu` is installed.

---

## Game Rules

### Stack cushions directly on one another without block support
- ID
    - `bettercushionplacement:cushions_support_each_other`
- Name
    - Cushions Support Each Other
- Description
    - Instead of only being supported by blocks, cushions can also be supported by other cushions, allowing direct stacking.
- Default
    - Disabled

### Background Explanation

Vanilla cushion support is as follows [Cushion_wouldSuriveAt](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/entity/decoration/Cushion#L155-163):
- [L156-158](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/entity/decoration/Cushion#L156-158) Make an anchor box by taking the bounding box a cushion is or might be, and slightly horizontally shrinking it and vertically expanding it down by 1/4 pixel.
- [159](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/entity/decoration/Cushion#159) Iterate the blocks intersecting that area additionally expanded down by another 4 pixels.
    - [160](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/entity/decoration/Cushion#160) For each block, get its general shape.
    - [161](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/entity/decoration/Cushion#161) Consider it supported if that shape is not empty and if it intersects the anchor box.

If no block support is found, this game rule additionally checks for other cushion entities below it, and considers it supported if one is found.

---

| Flush placement w/o datapack                                                                                                                                        | Close to flush via a sign                                                                                                                                                         |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/cushion_flush_with_block.png?raw=true" alt="Cushion Flush With Block"> | <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/cushion_almost_flush_with_block.png?raw=true" alt="Cushion Almost Flush With Block"> |

### Bypass block tags for inner wall sub-pixel placement with a game rule
- ID
    - `bettercushionplacement:allow_inner_wall_cushion_placement`
- Name
    - Allow Inner Wall Cushion Placement
- Description
    - Ignores `#cushion_uses_collision_shape` block tags, thus allowing sub-pixel cushion placement on the inner walls of cauldrons, composters, and hoppers without a data pack.
- Default
    - Disabled

### Background Explanation

Vanilla block interaction behavior is as follows [BlockGetter_clipWithInteractionOverride](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/BlockGetter#L84-96):
- [L87](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/BlockGetter#L87) Raytrace a block's main shape.
- [L88](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/BlockGetter#L88) Check for hit.
   - [L89](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/BlockGetter#L89) If hit, raytrace the block's interaction shape.
   - [L90](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/BlockGetter#L90) Check for hit, and check if it's closer to the eyes than the main shape hit.
      - [L91](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/BlockGetter#L91) If the interaction shape hit is closer than main shape hit, substitute the direction of the hit result with the new direction.

Blocks by default have no interaction shape [BlockBehaviour_getInteractionShape](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/block/state/BlockBehaviour#L296-298), with the following exceptions:
- Cauldrons [getInteractionShape](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/block/AbstractCauldronBlock#L71-74)
- Composters [getInteractionShape](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/block/ComposterBlock#L102-105)
- Hoppers [getInteractionShape](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/block/HopperBlock#L72-75)
- Scaffolding [getInteractionShape](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/block/ScaffoldingBlock#L67-70)

Whether returning a full block shape or just the inner void shape, all of these blocks have interactions shapes with solid flat tops flush with the top of the block space.

| Composter interaction shape                                                                                                                                               | Hopper interaction shape                                                                                                                                            |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/composter_interaction_shape.png?raw=true" alt="Composter Interaction Shape"> | <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/hopper_interaction_shape.png?raw=true" alt="Hopper Interaction Shape"> |

The only way to get a raytrace hit on the inner walls of the shapes of these blocks is to get an even closer hit on the top face of their interaction shapes. This means that any inner hit will have the expected inner location vector, but with a direction of `Direction.UP`. So although cushions require interacting with an upward face [CushionItem_useOn#L38](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/item/CushionItem#L38-39), these blocks uniquely bypass this requirement.

While sub-pixel placement is still possible (on signs [WallSignBlock_SHAPES](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/block/WallSignBlock#L27), for example), placement on horizontal faces and arbitrary Y positioning was not intended. This is why Mojang added the `#cushion_uses_collision_shape` block tag, which forces the use of the collision shape raytrace result [CushionItem_recalculateContextForSpecialCollisionShapes#L81-88](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/item/CushionItem#L81-88).

| Wall collision placement                                                                                                                                            | Soul Sand collision placement                                                                                                                                                 |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/wall_collision_placement.png?raw=true" alt="Wall Collision Placement"> | <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/soul_sand_collision_placement.png?raw=true" alt="Soul Sand Collision Placement"> |

Note that raytrace is from the eyes [L84](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/item/CushionItem#L84) to the slightly past the exiting hit result vector [L85-86](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/item/CushionItem#L85-86), and that hit failure defaults to the original hit result [L88](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/item/CushionItem#L88). This means that when a collision shape hit is closer to the eyes than the main shape hit, as with wall blocks [WallBlock_collisionShapes](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/block/WallBlock#L58), the collision shape will be used. But when the main shape hit is closer, as with soul sand blocks [SoulSandBlock_SHAPE](https://mcsrc.dev/1/26.3-snapshot-5/net/minecraft/world/level/block/SoulSandBlock#L13), the main shape will be used in spite of having the tag.

---
## Server-only Installation
### If this mod is installed on a server, but not a client, everything will work as intended, with the following purely visual exceptions:
1. The player's hand will swing
   - Even when cushion placement fails due to intersection with an existing cushion.
2. The player's hand will not swing
   - When placing a cushion on the inner wall of a cauldron, composter, or hopper.
   - When stacking a cushion on another cushion.