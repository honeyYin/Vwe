ALTER TABLE Paper ADD COLUMN isRecom INT(1);

UPDATE Paper set isRecom = 0;

update paper set pregStage = 0 where pregStage is NULL;