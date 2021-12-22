/*
 * MegaMekLab
 * Copyright (c) 2021 - The MegaMek Team. All Rights Reserved.
 *
 * This program is  free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */
package megameklab.com.ui.protoMek;

import megamek.common.EquipmentType;
import megamek.common.MiscType;
import megamek.common.Mounted;
import megameklab.com.ui.EntitySource;
import megameklab.com.ui.generalUnit.AbstractEquipmentTab;
import megameklab.com.ui.util.AbstractEquipmentDatabaseView;
import megameklab.com.util.UnitUtil;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/*
 * The Equipment Tab for ProtoMek units showing the equipment database and the current loadout list.
 *
 * @author Simon (Juliez)
 */
public class PMEquipmentTab extends AbstractEquipmentTab {

    public PMEquipmentTab(EntitySource eSource) {
        super(eSource);
    }

    @Override
    protected AbstractEquipmentDatabaseView getEquipmentDatabaseView() {
        return new PMEquipmentDatabaseView(eSource);
    }

    @Override
    protected List<Mounted> getLoadout() {
        List<Mounted> result = new ArrayList<>();
        result.addAll(getEntity().getWeaponList());
        result.addAll(getEntity().getAmmo());
        result.addAll(getEntity().getMisc().stream().filter(this::showInLoadout).collect(toList()));
        return result;
    }

    private boolean showInLoadout(Mounted mount) {
        EquipmentType etype = mount.getType();
        //TODO: Clean up: Not all of these may be relevant for PM
        return !(UnitUtil.isHeatSink(mount)
                || etype.hasFlag(MiscType.F_JUMP_JET)
                || etype.hasFlag(MiscType.F_JUMP_BOOSTER)
                || etype.hasFlag(MiscType.F_TSM)
                || etype.hasFlag(MiscType.F_INDUSTRIAL_TSM)
                || etype.hasFlag(MiscType.F_MASC)
                || etype.hasFlag(MiscType.F_CHASSIS_MODIFICATION)
                || UnitUtil.isArmorOrStructure(etype));
    }

}