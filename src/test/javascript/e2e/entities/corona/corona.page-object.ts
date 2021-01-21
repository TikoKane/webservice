import { element, by, ElementFinder } from 'protractor';

export class CoronaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-corona div table .btn-danger'));
  title = element.all(by.css('jhi-corona div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class CoronaUpdatePage {
  pageTitle = element(by.id('jhi-corona-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nombrecasparjourInput = element(by.id('field_nombrecasparjour'));
  caspositifInput = element(by.id('field_caspositif'));
  cascommunautaireInput = element(by.id('field_cascommunautaire'));
  casgraveInput = element(by.id('field_casgrave'));
  guerisonInput = element(by.id('field_guerison'));
  decesInput = element(by.id('field_deces'));
  cascontactInput = element(by.id('field_cascontact'));
  casimporteInput = element(by.id('field_casimporte'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNombrecasparjourInput(nombrecasparjour: string): Promise<void> {
    await this.nombrecasparjourInput.sendKeys(nombrecasparjour);
  }

  async getNombrecasparjourInput(): Promise<string> {
    return await this.nombrecasparjourInput.getAttribute('value');
  }

  async setCaspositifInput(caspositif: string): Promise<void> {
    await this.caspositifInput.sendKeys(caspositif);
  }

  async getCaspositifInput(): Promise<string> {
    return await this.caspositifInput.getAttribute('value');
  }

  async setCascommunautaireInput(cascommunautaire: string): Promise<void> {
    await this.cascommunautaireInput.sendKeys(cascommunautaire);
  }

  async getCascommunautaireInput(): Promise<string> {
    return await this.cascommunautaireInput.getAttribute('value');
  }

  async setCasgraveInput(casgrave: string): Promise<void> {
    await this.casgraveInput.sendKeys(casgrave);
  }

  async getCasgraveInput(): Promise<string> {
    return await this.casgraveInput.getAttribute('value');
  }

  async setGuerisonInput(guerison: string): Promise<void> {
    await this.guerisonInput.sendKeys(guerison);
  }

  async getGuerisonInput(): Promise<string> {
    return await this.guerisonInput.getAttribute('value');
  }

  async setDecesInput(deces: string): Promise<void> {
    await this.decesInput.sendKeys(deces);
  }

  async getDecesInput(): Promise<string> {
    return await this.decesInput.getAttribute('value');
  }

  async setCascontactInput(cascontact: string): Promise<void> {
    await this.cascontactInput.sendKeys(cascontact);
  }

  async getCascontactInput(): Promise<string> {
    return await this.cascontactInput.getAttribute('value');
  }

  async setCasimporteInput(casimporte: string): Promise<void> {
    await this.casimporteInput.sendKeys(casimporte);
  }

  async getCasimporteInput(): Promise<string> {
    return await this.casimporteInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CoronaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-corona-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-corona'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
